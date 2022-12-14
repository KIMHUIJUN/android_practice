package com.cookandroid.a20182376

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.cookandroid.a20182376.databinding.FragmentPhotoBinding

private const val ARG_URI = "uri"

class PhotoFragment : Fragment() {
    private lateinit var uri: Uri
    private val binding by lazy { FragmentPhotoBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getParcelable<Uri>(ARG_URI)?.let {
            uri = it
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return binding.root

    }

    companion object {
        @JvmStatic
        fun newInstance(uri: Uri) =
            PhotoFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_URI, uri)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageView = binding.imageView

        val descriptor = requireContext().contentResolver.openFileDescriptor(uri,"r")
        descriptor?.use {
            val bitmap = BitmapFactory.decodeFileDescriptor(descriptor.fileDescriptor)
            imageView.load(bitmap)
        }
    }
}