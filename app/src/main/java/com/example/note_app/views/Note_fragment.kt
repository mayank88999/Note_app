package com.example.note_app.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.note_app.data.Note
import com.example.note_app.models.NoteDao
import com.example.note_app.R
import com.example.note_app.databinding.FragmentNoteFragmentBinding


class Note_fragment : Fragment() {
  private lateinit var binding: FragmentNoteFragmentBinding
    private val noteDao by lazy { NoteDao(requireContext()) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding= FragmentNoteFragmentBinding.inflate(inflater,container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnsavenote.setOnClickListener {

            val title = binding.editTextText.text.toString()
            val content = binding.editTextText2.text.toString()
            val newNote = Note(
                id = 0,
                title = title,
                content = content
            )
            noteDao.addNote(newNote)
            findNavController().navigate(R.id.action_note_fragment_to_notes_fragment)
        }
    }


}