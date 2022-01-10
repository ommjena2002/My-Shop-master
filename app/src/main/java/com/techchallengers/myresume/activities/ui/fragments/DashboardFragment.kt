package com.techchallengers.myresume.activities.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.facebook.shimmer.ShimmerFrameLayout
import com.techchallengers.myresume.R
import com.techchallengers.myresume.activities.CartListActivity
import com.techchallengers.myresume.activities.ProductDetailsActivity
import com.techchallengers.myresume.activities.SettingsActivity
import com.techchallengers.myresume.activities.ui.adapters.DashboardItemsListAdapter
import com.techchallengers.myresume.databinding.FragmentDashboardBinding
import com.techchallengers.myresume.firestore.FireStoreClass
import com.techchallengers.myresume.model.Product
import com.techchallengers.myresume.utils.Constants

class DashboardFragment : BaseFragment() {
    private val shimmerLayout: ShimmerFrameLayout? = null
//    private lateinit var dashboardViewModel: DashboardViewModel
    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        dashboardViewModel =
//            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_dashboard,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_setting ->{
                startActivity(Intent(activity, SettingsActivity::class.java))
                return true
            }
            R.id.action_cart->{
                startActivity(Intent(activity,CartListActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()

//        binding.shimmerViewContainer.startShimmerAnimation()

        getDashboardItemsList()
    }


    fun successDashboardItemList(dashboardItemsList : ArrayList<Product>){

        binding.shimmerViewContainer.visibility = View.GONE
        binding.shimmerViewContainer.stopShimmer()
//        hideProgressDialog()

//        for(i in dashboardItemsList){
//            Log.i("Item Title ::", i.title)
//        }

        if (dashboardItemsList.size > 0 ){
            binding.rvMyProductsItems.visibility = View.VISIBLE
            binding.tvNoProductsFound.visibility = View.GONE

            val adapter = DashboardItemsListAdapter(requireActivity(),dashboardItemsList)
            binding.rvMyProductsItems.layoutManager = GridLayoutManager(activity,2)
            binding.rvMyProductsItems.setHasFixedSize(true)
            binding.rvMyProductsItems.adapter = adapter

            adapter.setOnClickListener(object : DashboardItemsListAdapter.OnClickListener{
                override fun onClick(position: Int, product: Product) {
                    val intent = Intent(requireActivity(),ProductDetailsActivity::class.java)
                    intent.putExtra(Constants.EXTRA_PRODUCT_ID,product.product_id)
                    intent.putExtra(Constants.EXTRA_PRODUCT_OWNER_ID,product.user_id)
                    startActivity(intent)
                }

            })

        }else{
            binding.rvMyProductsItems.visibility = View.GONE
            binding.tvNoProductsFound.visibility = View.VISIBLE
        }

    }
    private fun getDashboardItemsList(){
//        showProgressDialog(getString(R.string.please_wait))

        binding.shimmerViewContainer.startShimmer()

        FireStoreClass().getDashboardItemsList(this)
    }
}