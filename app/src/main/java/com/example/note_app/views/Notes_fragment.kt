package com.example.note_app.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.note_app.models.NoteAdapter
import com.example.note_app.models.NoteDao
import com.example.note_app.R
import com.example.note_app.databinding.FragmentNotesFragmentBinding


class Notes_fragment : Fragment() {
private lateinit var binding: FragmentNotesFragmentBinding
    private lateinit var noteAdapter: NoteAdapter
    private val noteDao by lazy { NoteDao(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentNotesFragmentBinding.inflate(inflater,container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        binding.addNoteButton.setOnClickListener {
           findNavController().navigate(R.id.action_notes_fragment_to_note_fragment)
        }

        loadNotes()
    }

    private fun setupRecyclerView() {
        noteAdapter = NoteAdapter(noteDao,requireContext())
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = noteAdapter
        }
    }

    private fun loadNotes() {
        val notes = noteDao.getAllNotes()
        noteAdapter.submitList(notes)
    }
}