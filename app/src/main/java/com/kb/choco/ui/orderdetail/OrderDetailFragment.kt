package com.kb.choco.ui.orderdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.kb.choco.R
import com.kb.choco.databinding.OrderDetailFragmentBinding
import com.kb.choco.util.ORDER_ID_KEY
import com.kb.choco.util.extensions.appendCurrency
import kotlinx.android.synthetic.main.order_detail_fragment.*

class OrderDetailFragment : Fragment() {

    private val viewModel: OrderDetailViewModel by activityViewModels()
    private var orderItemBinding: OrderDetailFragmentBinding? = null
    private val listAdapter = OrderDetailProductListAdapter()
    private var orderId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.order_detail_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        orderId = arguments?.getInt(ORDER_ID_KEY) ?: 0
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        val binding = OrderDetailFragmentBinding.bind(view)
        orderItemBinding = binding
        initProductsList()
        observeData()
    }

    private fun initProductsList() {
        orderItemBinding?.recyclerView?.apply {
            adapter = listAdapter
            val decoration =
                DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL)
            addItemDecoration(decoration)
        }
        btnUpdateOrder.setOnClickListener {
            listAdapter.activateCounter(true)
            btnSaveUpdate.visibility = View.VISIBLE
            btnUpdateOrder.visibility = View.GONE
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.miOrders).isVisible = false
    }

    private fun observeData() {
        with(viewModel) {
            getProductsInOrder(orderId)

            productsList.observe(viewLifecycleOwner) {
                listAdapter.setProducts(it)
            }
            listAdapter.totalPrice.observe(viewLifecycleOwner){
                tvTotalPrice.text = it.toString().appendCurrency()
            }
            listAdapter.notifyDataSetChanged()


            isError.observe(viewLifecycleOwner) {
                if (it) {
                    showPopup(
                        requireContext(),
                        getString(R.string.error_title),
                        getString(R.string.error_fetching_data)
                    )
                }
            }

        }

    }

}