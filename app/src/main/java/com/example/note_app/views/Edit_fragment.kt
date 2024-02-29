package com.example.note_app.views

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.note_app.data.Note
import com.example.note_app.models.NoteDao
import com.example.note_app.R
import com.example.note_app.databinding.FragmentEditFragmentBinding



class Edit_fragment : Fragment() {
private lateinit var binding: FragmentEditFragmentBinding

    private val noteDao by lazy { NoteDao(requireContext()) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentEditFragmentBinding.inflate(inflater,container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
val sh:SharedPreferences= requireContext().getSharedPreferences("NoteData", Context.MODE_PRIVATE)
        val noteId = sh.getLong("noteId",1)
        val noteTitle = sh.getString("noteTitle","") ?: ""
        val noteContent = sh.getString("noteContent","") ?: ""

        binding.editTexttitle.setText(noteTitle)
        binding.editTextcontent.setText(noteContent)

        binding.btnupdate.setOnClickListener {
            val title = binding.editTexttitle.text.toString()
            val content = binding.editTextcontent.text.toString()
            val newNote= Note(noteId,title,content)
            noteDao.updateNote(newNote)
            findNavController().navigate(R.id.action_edit_fragment_to_notes_fragment)
        }
    }
}