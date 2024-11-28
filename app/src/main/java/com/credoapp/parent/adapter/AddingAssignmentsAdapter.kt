package com.credoapp.parent.adapter

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.credoapp.parent.R
import com.credoapp.parent.common.Constants
import com.credoapp.parent.model.GlobalResponse
import com.credoapp.parent.model.addAssignment.AddAssignmentModel
import com.credoapp.parent.model.addAssignment.DeleteAssignmentImageRequest
import com.credoapp.parent.retrofit.ITutorSource
import com.credoapp.parent.ui.AddAssignmentActivity

import kotlinx.android.synthetic.main.adding_assignment.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddingAssignmentsAdapter(val addAssignmentActivity: AddAssignmentActivity, private val addAssignmentModels: MutableList<AddAssignmentModel>) :RecyclerView.Adapter<AddingAssignmentsAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddingAssignmentsAdapter.MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.adding_assignment, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AddingAssignmentsAdapter.MyViewHolder, position: Int) {
        Glide.with(addAssignmentActivity).load(addAssignmentModels[position].imgUrl).placeholder(R.drawable.logo_two).into(holder.itemView.assignmentImage)
    }

    override fun getItemCount(): Int {
        return addAssignmentModels.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.deleteAssignmentImage.setOnClickListener {

                shopPopUpToDelete(itemView, addAssignmentModels[adapterPosition].imgId,adapterPosition)

            }
        }
    }

    private fun shopPopUpToDelete(itemView: View, imgId: String?, adapterPosition: Int) {

        val builder = AlertDialog.Builder(itemView.context)
        builder.setTitle("Alert !")
        builder.setMessage("Are you sure you want to delete this pic")
        builder.setPositiveButton("Yes") { dialog: DialogInterface, id: Int ->
            deleteImage(imgId)
            addAssignmentModels.removeAt(adapterPosition)
            notifyItemRemoved(adapterPosition)
            notifyItemRangeChanged(adapterPosition, addAssignmentModels.size)
            dialog.dismiss()
        }
        builder.setNegativeButton("No") { dialog: DialogInterface, id: Int -> dialog.dismiss() }
        builder.show()
        builder.setCancelable(false)
    }

    fun deleteImage(imgId: String?) {
        val request = DeleteAssignmentImageRequest()
        request.assignmentId = addAssignmentActivity.assignmentId
        request.imgId = imgId
        if (Constants.haveInternet(addAssignmentActivity)) {
            ITutorSource.getRestAPI().deleteAssignmentImage(request).enqueue(object : Callback<GlobalResponse?> {
                override fun onResponse(call: Call<GlobalResponse?>, response: Response<GlobalResponse?>) {
                    if (response.isSuccessful) {
                        Toast.makeText(addAssignmentActivity, response.body()?.description, Toast.LENGTH_SHORT).show()
//                        deleteAssignmentImageResponse(Objects.requireNonNull(response.body()))
                    } else {
                        Toast.makeText(addAssignmentActivity, R.string.unexpected_error, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<GlobalResponse?>, t: Throwable) {
                    t.printStackTrace()
                    Toast.makeText(addAssignmentActivity, R.string.unexpected_error, Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Constants.IntenetSettings(addAssignmentActivity)
        }
    }
}