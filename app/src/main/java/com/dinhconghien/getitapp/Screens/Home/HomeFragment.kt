package com.dinhconghien.getitapp.Screens.Home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dinhconghien.getitapp.Adapter.BrandLapName_Adapter
import com.dinhconghien.getitapp.Adapter.SliderImage_Adapter
import com.dinhconghien.getitapp.Models.BrandLapName
import com.dinhconghien.getitapp.Models.SliderImage
import com.dinhconghien.getitapp.R
import com.dinhconghien.getitapp.Screens.Cart.CartActivity
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var sliderView: SliderView
    lateinit var adapterImageSlider: SliderImage_Adapter
    var listImageSlider = ArrayList<SliderImage>()

    lateinit var adapterBrandLap: BrandLapName_Adapter
    lateinit var reclerView_brandLap: RecyclerView
    lateinit var brandLap: BrandLapName
    var listBrandName = ArrayList<BrandLapName>()

    lateinit var imv_cart: ImageView

    val TAG = "Check HomeFragment 's Lifecycle"
    val listSliderTAG = "Check list Slider"

    @SuppressLint("LongLogTag")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "Oncreate is being called !")

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("LongLogTag")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        initComponents(view)

        adapterImageSlider = SliderImage_Adapter(view.context, listImageSlider)
        initForSliderImage()
        Log.d(TAG, "OncreateView is being called !")

        getBrandNameItem(view)

        imv_cart.setOnClickListener {
            val intent = Intent(view.context,
                CartActivity::class.java)
            startActivity(intent)

        }

        return view
    }

    private fun initComponents(view: View) {
        sliderView = view.findViewById(R.id.imageSlider)
        reclerView_brandLap = view.findViewById(R.id.reclerView_itemBrandName_homeScreen)
        imv_cart = view.findViewById(R.id.imv_cart_homeScreen)
    }

    private fun initForSliderImage() {
        sliderView.setSliderAdapter(adapterImageSlider)
        sliderView.setIndicatorAnimation(IndicatorAnimationType.SWAP)
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        sliderView.autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_RIGHT
        sliderView.indicatorUnselectedColor = Color.GRAY
        sliderView.scrollTimeInSec = 3
        sliderView.isAutoCycle = true
        sliderView.startAutoCycle()
    }

    private fun getBrandNameItem(view: View) {
        adapterBrandLap = BrandLapName_Adapter(view.context, listBrandName)
        val gridLayoutManager =
            GridLayoutManager(view.context, 3, LinearLayoutManager.VERTICAL, false)
        reclerView_brandLap.layoutManager = gridLayoutManager
        reclerView_brandLap.setHasFixedSize(true)
        reclerView_brandLap.adapter = adapterBrandLap

    }

    private fun addImageSliderItems() {
        val sliderImage = SliderImage(R.drawable.laptopmacbook_slider, "Laptop Macbook Pro")
        listImageSlider.add(sliderImage)

        val sliderImage2 = SliderImage(R.drawable.laptopacer_slider, "Laptop Acer")
        listImageSlider.add(sliderImage2)

        val sliderImage3 = SliderImage(R.drawable.laptopasus_slider, "Laptop Asus")
        listImageSlider.add(sliderImage3)

        val sliderImage4 = SliderImage(R.drawable.laptopdell_slider, "Laptop Dell")
        listImageSlider.add(sliderImage4)

        val sliderImage5 = SliderImage(R.drawable.laptophp_slider, "Laptop HP")
        listImageSlider.add(sliderImage5)

        val sliderImage6 = SliderImage(R.drawable.laptoplenovo_slider, "Laptop Lenovo")
        listImageSlider.add(sliderImage6)
        Log.d(listSliderTAG, "Current size is ${listImageSlider.size}")
    }

    // add brandLap items for inspection
    private fun addBrandLapItem() {
        brandLap = BrandLapName(R.drawable.hp, "Laptop HP")
        listBrandName.add(brandLap)

        brandLap = BrandLapName(R.drawable.dell, "Laptop Dell")
        listBrandName.add(brandLap)

        brandLap = BrandLapName(R.drawable.acer, "Laptop Acer")
        listBrandName.add(brandLap)

        brandLap = BrandLapName(R.drawable.lenovo, "Laptop Lenovo")
        listBrandName.add(brandLap)

        brandLap = BrandLapName(R.drawable.mac, "Laptop Mac")
        listBrandName.add(brandLap)

        brandLap = BrandLapName(R.drawable.asus, "Laptop Asus")
        listBrandName.add(brandLap)

    }

    @SuppressLint("LongLogTag")
    override fun onAttach(context: Context) {
        super.onAttach(context)
        addImageSliderItems()
        addBrandLapItem()
        Log.d(TAG, "OnAttach is being called !")
    }

    @SuppressLint("LongLogTag")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(TAG, "onActivityCreated is being called !")
    }

    @SuppressLint("LongLogTag")
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart is being called !")
    }

    @SuppressLint("LongLogTag")
    override fun onStop() {
        super.onStop()
        adapterImageSlider.deleteAllItem()
        Log.d(TAG, "onStop is being called !")
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}