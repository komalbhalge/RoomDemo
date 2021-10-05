package com.kb.choco.ui.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import com.kb.choco.R
import com.kb.choco.databinding.HomeFragmentBinding
import com.kb.choco.ui.session.SessionManagerUtil
import com.kb.moviedb.ui.main.adapter.ProductListAdapter
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : Fragment(), ProductSelectListener {

    private val viewModel: ProductListViewModel by activityViewModels()
    lateinit var navController: NavController
    private var productListBinding: HomeFragmentBinding? = null

    private val listAdapter = ProductListAdapter(
        productSelectListener = this
    )

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.miOrders).isVisible = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        navController = Navigation.findNavController(view)
        val binding = HomeFragmentBinding.bind(view)
        productListBinding = binding
        initProductsList()
        observeData()
    }

    private fun initProductsList() {
        productListBinding?.recyclerView?.apply {
            adapter = listAdapter
            val decoration =
                DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL)
            addItemDecoration(decoration)
        }
        btnCreateOrder.setOnClickListener {
            val list = listAdapter.getSelectedProducts()
            viewModel.insertSelectedProducts(list)
            listAdapter.unSelectProducts()
            Toast.makeText(
                requireContext(),
                getString(R.string.order_created_alert),
                Toast.LENGTH_LONG
            ).show()

            //Hide Create Order button after adding current order
            onProductAction(false)
        }
    }

    private fun observeData() {
        progressbarVisibility(true)
        SessionManagerUtil.getToken(requireContext())?.let { viewModel.getProductsList(it) }
        with(viewModel) {
            productsList.observe(viewLifecycleOwner) { products ->
                progressbarVisibility(false)
                if (products.isEmpty()) {
                    showEmptyLayout()
                } else {
                    listAdapter.setProducts(products)
                    listAdapter.notifyDataSetChanged()
                }
            }

            isError.observe(viewLifecycleOwner) {
                if (it) {
                    progressbarVisibility(false)
                    showEmptyLayout()
                    showPopup(
                        requireContext(),
                        getString(R.string.error_title),
                        getString(R.string.error_fetching_data)
                    )
                }
            }
        }

    }

    private fun progressbarVisibility(isVisible: Boolean) {
        if (isVisible) {
            progressbar.visibility = View.VISIBLE
        } else {
            progressbar.visibility = View.GONE
        }
    }

    override fun onProductAction(isSelected: Boolean) {
        if (isSelected) {
            btnCreateOrder.visibility = View.VISIBLE
        } else {
            btnCreateOrder.visibility = View.GONE
        }
    }

    private fun showEmptyLayout() {
        emptyLayout.visibility = View.VISIBLE
        listLayout.visibility = View.GONE
    }

}