package com.example.carousel

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carousel.map.ApiCaller
import com.example.carousel.map.ApiClient
import com.example.carousel.map.SearchQuery
import com.example.carousel.pojo.ResponseProductSearch
import com.example.carousel.pojo.ResponseProductSearchFilters
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment : Fragment() {
    //private val baseUrl = "http://54.165.207.44:8080"
    private var lastQuery = "fashion"   // a default query
    private lateinit var spinnerAdapter: ArrayAdapter<String>
    private lateinit var listViewBrandAdapter: ArrayAdapter<String>
    private var initializedView = false
    private var initializedViewFilter = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var sort = ""

        val spinner = sort_spinner
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.sort_options,
            android.R.layout.simple_spinner_item
        )
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                spinner.adapter = adapter
                spinnerAdapter = spinner.adapter as ArrayAdapter<String>
            }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                if (initializedView ==  false)
                {
                    initializedView = true;
                }
                else {
                    sort = context?.resources!!.getStringArray(R.array.sort_options).get(pos)
                    searchCall(lastQuery, sort)
                }
            }

        }

        filterButton.setOnClickListener {
            drawer_layout.openDrawer(Gravity.RIGHT);
        }
        expandable_price.visibility = View.GONE
        expandable_rating.visibility = View.GONE
        color_container.visibility = View.GONE
        size_container.visibility = View.GONE
        brand_container.visibility = View.GONE


        price_filter.setOnClickListener {
            if(expandable_price.visibility == View.GONE) {
                expandable_price.visibility = View.VISIBLE
            }
            else {
                expandable_price.visibility = View.GONE

            }
        }

        rating_filter.setOnClickListener {
            if(expandable_rating.visibility == View.GONE) {
                expandable_rating.visibility = View.VISIBLE
            }
            else {
                expandable_rating.visibility = View.GONE

            }
        }

        color_filter.setOnClickListener {
            if(color_container.visibility == View.GONE) {
                color_container.visibility = View.VISIBLE
            }
            else {
                color_container.visibility = View.GONE

            }
        }
        size_filter.setOnClickListener {
            if(size_container.visibility == View.GONE) {
                size_container.visibility = View.VISIBLE
            }
            else {
                size_container.visibility = View.GONE

            }
        }
        brand_filter.setOnClickListener {
            if(brand_container.visibility == View.GONE) {
                brand_container.visibility = View.VISIBLE
            }
            else {
                brand_container.visibility = View.GONE

            }
        }


        button_0_50.setOnClickListener {
            //button_0_50.setBackgroundColor(Color.parseColor("#FF1FEAD7"))
            min_price.setText("0")
            max_price.setText("50")
        }
        button_50_100.setOnClickListener {
            min_price.setText("50")
            max_price.setText("100")
        }
        button_100_250.setOnClickListener {
            min_price.setText("100")
            max_price.setText("250")
        }
        button_250_500.setOnClickListener {
            min_price.setText("250")
            max_price.setText("500")
        }
        button_500_plus.setOnClickListener {
            min_price.setText("500")
            max_price.setText("")
        }

        apply_button.setOnClickListener {
            /*expandable_price.visibility = View.GONE
            expandable_rating.visibility = View.GONE
            color_container.visibility = View.GONE
            size_container.visibility = View.GONE
            brand_container.visibility = View.GONE*/
        }


        //val products = ArrayList<Product>()
        //val adapter = ProductsAdapter(products)
        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if(query != "") {
                    lastQuery = query
                }
                searchCall(query, sort)
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    private fun searchCall(query: String, sort: String) {

        val apiCallerProductSearch: ApiCaller<ResponseProductSearch> = ApiCaller(activity)
        //apiCallerLogin.Button = login_button

        apiCallerProductSearch.Caller = ApiClient.getClient.productSearch(SearchQuery(query), sort)
        apiCallerProductSearch.Success = { it ->
            if (it != null) {
                activity?.runOnUiThread(Runnable { //Handle UI here
                    val products = ArrayList<Product>()
                    for(item in it.data) {
                        products.add(responseToProductSearch(item, item.mainProduct[0]))
                    }
                    createProductList(products, results)
                    print("PRODUCTS")
                    print(products)
                })
            }
        }
        apiCallerProductSearch.Failure = {}
        apiCallerProductSearch.run()


        searchFiltersCall(query)
    }

    private fun searchFiltersCall(query: String) {
        val apiCallerProductSearchFilters: ApiCaller<ResponseProductSearchFilters> = ApiCaller(activity)
        //apiCallerLogin.Button = login_button

        apiCallerProductSearchFilters.Caller = ApiClient.getClient.productSearchFilters(SearchQuery(query))
        apiCallerProductSearchFilters.Success = { it ->
            if (it != null) {
                activity?.runOnUiThread(Runnable { //Handle UI here

                    color_container.removeAllViews()
                    brand_container.removeAllViews()
                    size_container.removeAllViews()

                    for(brand in it.data.brands) {
                        val newItem = CheckBox(requireContext())
                        newItem.text = brand
                        newItem.layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                        brand_container.addView(newItem)
                    }

                    for(param in it.data.parameters) {
                        var myContainer: LinearLayout
                        if (param.name == "color") {
                            myContainer = color_container
                        }
                        else {
                            myContainer = size_container
                        }
                        for(v in param.value) {
                            val newItem = CheckBox(requireContext())
                            newItem.text = v
                            newItem.layoutParams = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                            )
                            myContainer.addView((newItem))
                        }
                    }





                    /*val filters = ArrayList<String, ArrayList<String>>()
                    for(item in it.data.parameters) {
                        item.name
                        item.value
                        //filters.add(responseToProductSearch(item, item.mainProduct[0]))
                    }
                    //createProductList(products, results)*/
                })
            }
        }
        apiCallerProductSearchFilters.Failure = {}
        apiCallerProductSearchFilters.run()

    }

    private fun createProductList(products: ArrayList<Product>, recyclerId: RecyclerView){
        val adapter = ProductsAdapter(products)
        recyclerId.apply {
            layoutManager = GridLayoutManager(this.context, 2)
            setAdapter(adapter)
        }
        adapter.onItemClick = { product ->
            val intent = Intent(this.context, ProductPageActivity::class.java)
            intent.putExtra("product",product)
            startActivity(intent)
        }
    }

}