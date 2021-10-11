package za.co.shepherd.weatherapp.ui.search.response

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import za.co.shepherd.weatherapp.core.BaseAdapter
import za.co.shepherd.weatherapp.databinding.ItemSearchResponseBinding
import za.co.shepherd.weatherapp.db.entities.CitiesForSearchEntity

class SearchResponseAdapter(private val callBack: (CitiesForSearchEntity) -> Unit) : BaseAdapter<CitiesForSearchEntity>(
    diffCallback
) {

    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        val mBinding = ItemSearchResponseBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        val viewModel = SearchResponseItemViewModel()
        mBinding.viewModel = viewModel

        mBinding.rootView.setOnClickListener {
            mBinding.viewModel?.item?.get()?.let {
                callBack.invoke(it)
            }
        }
        return mBinding
    }

    override fun bind(binding: ViewDataBinding, position: Int) {
        (binding as ItemSearchResponseBinding).viewModel?.item?.set(getItem(position))
        binding.executePendingBindings()
    }
}

val diffCallback = object : DiffUtil.ItemCallback<CitiesForSearchEntity>() {
    override fun areContentsTheSame(oldItem: CitiesForSearchEntity, newItem: CitiesForSearchEntity): Boolean =
        oldItem == newItem

    override fun areItemsTheSame(oldItem: CitiesForSearchEntity, newItem: CitiesForSearchEntity): Boolean =
        oldItem.name == newItem.name
}
