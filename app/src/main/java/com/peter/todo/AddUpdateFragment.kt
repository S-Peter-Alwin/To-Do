package com.peter.todo

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.peter.todo.databinding.FragmentAddUpdateBinding

class AddUpdateFragment : Fragment() {

    companion object {
        fun newInstance() = AddUpdateFragment()
    }

    private lateinit var binding: FragmentAddUpdateBinding
    private lateinit var viewModel: AddUpdateViewModel
    private lateinit var data: ToDoEntity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddUpdateBinding.inflate(layoutInflater, container, false)
        val toDoDao = AppDatabase.getInstance(requireContext()).todoDao()
        val repository = AddUpdateRepository(TodoServices.taskApiService,toDoDao,requireContext())
        viewModel = ViewModelProvider(this, AddUpdateViewModelFactory(repository)).get(AddUpdateViewModel::class.java)
        val action = requireArguments().getString("action")
        if (action.equals("Add",true)){
            binding.addUpdate.setText("ADD")
            binding.delete.visibility = View.GONE
        }
        else if(action.equals("Update",true)){
            binding.addUpdate.setText("UPDATE")
            binding.delete.visibility = View.VISIBLE
             data = requireArguments().getSerializable("todo") as ToDoEntity
            binding.etTodoTitle.setText(data.todo)
            binding.isCompleted.isChecked = data.completed
        }

        binding.addUpdate.setOnClickListener{
            if(!binding.etTodoTitle.text.toString().isEmpty()){
                if(binding.addUpdate.text.toString().equals("ADD",true)){
                    val todo = ToDoEntity(0,binding.etTodoTitle.text.toString(),2524,binding.isCompleted.isChecked)
                    viewModel.insert(todo)
                }
                else{
                    val todo = ToDoEntity(data.id,binding.etTodoTitle.text.toString(),data.userId,binding.isCompleted.isChecked)
                    viewModel.update(todo)
                }
            }
            else{
                Toast.makeText(requireContext(),"please fill the required field", Toast.LENGTH_SHORT).show()

            }

        }
        binding.delete.setOnClickListener{
            viewModel.delete(data)
        }
        viewModel.actionCompleteEvent.observe(viewLifecycleOwner, Observer {
            findNavController().popBackStack()
        })

        return binding.root
    }




}