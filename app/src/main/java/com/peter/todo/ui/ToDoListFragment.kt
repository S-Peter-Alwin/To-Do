package com.peter.todo.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.peter.todo.repository.ToDoListRepository
import com.peter.todo.viewmodel.ToDoListViewModel
import com.peter.todo.factory.ToDoListViewModelFactory
import com.peter.todo.network.TodoServices
import com.peter.todo.databinding.FragmentTodoListBinding
import com.peter.todo.db.AppDatabase
import com.peter.todo.util.NetworkUtils

class ToDoListFragment : Fragment()  {

    companion object {
        fun newInstance() = ToDoListFragment()
    }


    private lateinit var binding: FragmentTodoListBinding
    private lateinit var adapter : TodoListAdapter
    private lateinit var viewModel: ToDoListViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTodoListBinding.inflate(inflater, container, false)
        setUpViewModel()
        setUpRecyclerView()
        binding.addTodo.setOnClickListener {
            if(NetworkUtils.isOnline(requireContext())){
            val action = ToDoListFragmentDirections.actionToAddFragment()
            findNavController().navigate(action)}
            else{
                Toast.makeText(context,"No internet connection!", Toast.LENGTH_SHORT).show()

            }
        }

        if(NetworkUtils.isOnline(requireContext())){
            binding.addTodo.visibility = View.VISIBLE
        }
//        viewModel = by viewModels<ToDoListViewModel> { ToDoListViewModelFactory(repository) }
        return binding.root
    }

    private fun setUpViewModel(){
        val toDoDao = AppDatabase.getInstance(requireContext()).todoDao()
        val repository = ToDoListRepository(TodoServices.taskApiService,toDoDao,requireContext())
        viewModel = ViewModelProvider(this, ToDoListViewModelFactory(repository))
            .get(ToDoListViewModel::class.java)
        viewModel.loadingState.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }

        viewModel.allTasks.observe(viewLifecycleOwner, Observer {
            if(it.isEmpty()&& binding.progressBar.visibility == View.GONE){
                binding.progressBar.visibility = View.GONE
                binding.todoList.visibility = View.GONE
                binding.noTodoFound.visibility = View.VISIBLE
            }
            else{
                adapter.submitList(it)
                binding.noTodoFound.visibility = View.GONE
                binding.todoList.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
            }



        })
    }

    private fun setUpRecyclerView(){
        val toDoList: RecyclerView = binding.todoList
        adapter = TodoListAdapter {
            val action = ToDoListFragmentDirections.actionToUpdateFragment(it)
            findNavController().navigate(action)
        }
        toDoList.adapter = adapter
        toDoList.layoutManager = LinearLayoutManager(requireContext())
        toDoList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

                if (visibleItemCount + firstVisibleItem >= totalItemCount) {
                    viewModel.loadMore(totalItemCount)
                }
            }
        })
    }




}