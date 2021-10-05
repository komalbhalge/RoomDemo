package com.kb.choco.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.kb.choco.MainActivity
import com.kb.choco.R
import com.kb.choco.data.model.LoginRequest
import com.kb.choco.ui.session.SessionManagerUtil
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.android.synthetic.main.login_fragment.progressbar

class LoginFragment : Fragment() {

    lateinit var navController: NavController
    private val viewModel: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        setupUI()
        observeData()
    }

    fun initView(view: View) {
        navController = Navigation.findNavController(view)

        //Navigate to Home Screen if user is already logged in
        checkSessionValidity()

        //Enable options menu
        setHasOptionsMenu(true)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        //Hide menu options
        menu.findItem(R.id.miOrders).isVisible = false
        menu.findItem(R.id.miLogout).isVisible = false
    }

    private fun checkSessionValidity() {
        if (SessionManagerUtil.isSessionValid(requireContext())) {
            navController.navigate(R.id.action_loginFragment_to_homeFragment)
        }
    }

    private fun setupUI() {
        btn_login.setOnClickListener {
            progressbar.visibility = View.VISIBLE
            val email = tiUsername.text.toString()
            val password = tipassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.empty_login_message),
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }
            viewModel.login(LoginRequest(email = email, password = password))
        }
    }

    private fun observeData() {
        with(viewModel) {
            loginToken.observe(viewLifecycleOwner, { token ->
                if (!token.isNullOrEmpty()) {
                    progressbar.visibility = View.GONE
                    loginToken.value = null
                    SessionManagerUtil.startUserSession(requireContext(), token)

                    (activity as MainActivity).changeStartDestination()
                    val navOptions = NavOptions.Builder()
                        .setPopUpTo(R.id.homeFragment, true)
                        .build()
                    navController.navigate(
                        R.id.action_loginFragment_to_homeFragment,
                        null,
                        navOptions
                    )
                }
            })

            isError.observe(viewLifecycleOwner) {
                if (it) {
                    showPopup(
                        requireContext(),
                        getString(R.string.error_title),
                        getString(R.string.login_error)
                    )
                }

                progressbar.visibility = View.GONE
            }
            shouldShowNoInternetAlert.observe(viewLifecycleOwner) { shouldShow ->
                if (shouldShow) {
                    showPopup(
                        requireContext(),
                        getString(R.string.network_error),
                        getString(R.string.network_error_message)
                    )
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.loginToken.removeObservers(viewLifecycleOwner)
        viewModel.isError.removeObservers(viewLifecycleOwner)
    }

}