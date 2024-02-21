package com.peter.todo.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.peter.todo.R
import com.peter.todo.repository.AddUpdateRepository
import com.peter.todo.viewmodel.AddUpdateViewModel
import com.peter.todo.factory.AddUpdateViewModelFactory
import com.peter.todo.network.TodoServices
import com.peter.todo.databinding.FragmentAddUpdateBinding
import com.peter.todo.db.AppDatabase
import com.peter.todo.db.ToDoEntity

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
        setUpViewModel()
        setUpView()
        return binding.root
    }

   fun setUpViewModel (){
       val toDoDao = AppDatabase.getInstance(requireContext()).todoDao()
       val repository = AddUpdateRepository(TodoServices.taskApiService,toDoDao,requireContext())
       viewModel = ViewModelProvider(this, AddUpdateViewModelFactory(repository)).get(
           AddUpdateViewModel::class.java)
       viewModel.actionCompleteEvent.observe(viewLifecycleOwner, Observer {
           findNavController().popBackStack()
       })
   }
    fun setUpView(){
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
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Delete")
            builder.setMessage("Are you sure want to delete?")
            builder.setPositiveButton("Yes"){dialogInterface, which ->
                viewModel.delete(data)
            }
            builder.setNegativeButton("Cancel"){dialogInterface, which ->
                dialogInterface.dismiss()
            }
            val alertDialog: AlertDialog = builder.create()

            alertDialog.setCancelable(false)
            alertDialog.show()
            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(requireContext(),R.color.red))
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(requireContext(),R.color.red));



        }

    }


}