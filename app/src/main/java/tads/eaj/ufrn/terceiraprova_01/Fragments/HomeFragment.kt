package tads.eaj.ufrn.terceiraprova_01.Fragments

import android.os.Bundle
import android.text.Layout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import tads.eaj.ufrn.terceiraprova_01.R
import tads.eaj.ufrn.terceiraprova_01.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        binding.buttonLight.setOnClickListener {
          Navigation.findNavController(it).navigate(HomeFragmentDirections.actionHomeFragmentToLightFragment())
        }

        setHasOptionsMenu(true);
        return binding.root
    }
}