package fr.isen.soda.androidtoolbox

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_cycle_vie_.*


class CycleVieFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cycle_vie_, container, false)
    }
    private fun notification(message: String, isActive: Boolean) {
        if(isActive)
            text.text = message
        else
            Log.d("TAG", message)

    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        notification("onAttach fragement", false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        notification("onCreate fragement",  false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        notification("onAactivityCreated fragement", false)
    }
    override fun onStart() {
        super.onStart()
        notification("onStart fragement",  true)
    }

    override fun onResume() {
        super.onResume()
        notification("onResume fragement",  true)
    }

    override fun onPause() {
        super.onPause()
        notification("onPause fragement", false)
    }

    override fun onStop() {
        super.onStop()
        notification("onStop fragement", false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        notification("onDestroyView fragement", false)
    }

    override fun onDestroy() {
        super.onDestroy()
        notification("onDestroy fragement", false)
        Toast.makeText( activity , "onDestroy fragement",Toast.LENGTH_LONG).show()
    }

    override fun onDetach() {
        super.onDetach()
        notification("onDetach fragement", false)
    }
}