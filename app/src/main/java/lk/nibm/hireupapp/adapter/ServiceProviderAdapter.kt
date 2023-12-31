package lk.nibm.hireupapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import lk.nibm.hireupapp.R
import lk.nibm.hireupapp.common.CategoryDataManager
import lk.nibm.hireupapp.common.ServiceProviderDataManager
import lk.nibm.hireupapp.model.ServiceProviders

class ServiceProviderAdapter (private var itemList: List<ServiceProviders>) : RecyclerView.Adapter<ServiceProviderAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name : TextView
        private var category : TextView
        private var price : TextView
        private var address : TextView
        private var image : ImageView
        private var city : TextView
        private var district : TextView
        val cardView : CardView = itemView.findViewById(R.id.spCard)

        init {
            name = itemView.findViewById(R.id.txtSPCardServiceProviderName)
            category = itemView.findViewById(R.id.txtSPCardServiceCategory)
            price = itemView.findViewById(R.id.txtSPCardStartingPrice)
            address = itemView.findViewById(R.id.txtSPCardAddress)
            image = itemView.findViewById(R.id.imgSPCardProfilePicture)
            city = itemView.findViewById(R.id.txtSPCardCity)
            district = itemView.findViewById(R.id.txtSpCardProvince)


        }
        fun bind(item: ServiceProviders) {
            name.text = item.full_name
            category.text = CategoryDataManager.name
            price.text = item.price
            address.text = item.address
            city.text = item.city
            district.text = item.district
            Glide.with(itemView)
                .load(item.photoURL)
                .into(image)
        }


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ServiceProviderAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.service_provider_card, parent, false)
        return ServiceProviderAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ServiceProviderAdapter.ViewHolder, position: Int) {
//        var item = itemList[position]
        var item = itemList[position]
        holder.cardView.setOnClickListener {
            ServiceProviderDataManager.setProvider(item)
            val intent = Intent(holder.itemView.context, lk.nibm.hireupapp.activities.SP_details::class.java)
            holder.itemView.context.startActivity(intent)
        }
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun searchByName(searchList: List<ServiceProviders>){
        itemList = searchList   //change the list val to var
        notifyDataSetChanged()
    }
}