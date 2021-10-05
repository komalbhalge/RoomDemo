package com.kb.choco.ui.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import com.kb.choco.R
import com.kb.choco.databinding.OrderListFragmentBinding
import com.kb.choco.util.ORDER_ID_KEY
import kotlinx.android.synthetic.main.order_list_fragment.*

class OrderListFragment : Fragment() {

    lateinit var navController: NavController

    private val viewModel: OrderListViewModel by activityViewModels()
    private var orderItemBinding: OrderListFragmentBinding? = null

    private val orderListAdapter = OrderListAdapter(
        onItemClicked = { position -> onListItemClick(position) }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.order_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        initProductsList()
        observeData()
    }

    private fun initView(view: View) {
        setHasOptionsMenu(true)
        navController = Navigation.findNavController(view)
        val binding = OrderListFragmentBinding.bind(view)
        orderItemBinding = binding
    }

    private fun initProductsList() {
        orderItemBinding?.recyclerView?.apply {
            adapter = orderListAdapter
            val decoration =
                DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL)
            addItemDecoration(decoration)
        }

    }

    private fun observeData() {
        progressbarVisibility(true)

        with(viewModel) {

            getOrderList()

            orderList.observe(viewLifecycleOwner) { orders ->
                if (orders.isEmpty()) {
                    showEmptyLayout()
                } else {
                    orderListAdapter.setProducts(orders)
                    orderListAdapter.notifyDataSetChanged()
                    progressbarVisibility(false)
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

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.miOrders).isVisible = false
    }

    private fun onListItemClick(position: Int) {
        //Handle List item click
        viewModel.orderList.value?.let {
            val bundle = bundleOf(ORDER_ID_KEY to it[position].orderId)
            navController.navigate(R.id.from_order_to_orderDetail, bundle)
        }

    }

    private fun showEmptyLayout() {
        emptyLayout.visibility = View.VISIBLE
        listLayout.visibility = View.GONE
    }

    private fun progressbarVisibility(isVisible: Boolean) {
        if (isVisible) {
            progressbar.visibility = View.VISIBLE
        } else {
            progressbar.visibility = View.GONE
        }
    }
}