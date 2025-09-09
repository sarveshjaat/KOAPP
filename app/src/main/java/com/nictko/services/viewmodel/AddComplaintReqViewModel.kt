package com.nictko.services.viewmodel
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nictko.services.repository.UserRepository
import com.nictko.services.request.AddComplaintFeedbackRequest
import com.nictko.services.request.AddComplaintReqmodel
import com.nictko.services.request.ComplaintReportListRequest
import com.nictko.services.request.GetCspCategoryRequest
import com.nictko.services.response.AddComplaintResponse
import com.nictko.services.response.GetCspCategoryResponse
import com.nictko.services.response.compliantlistresponse.CompliantListResposne
import com.nictko.services.response.feedbackresposne.AddFeedbackResponse
import kotlinx.coroutines.launch

class AddComplaintReqViewModel   (application: Application) : AndroidViewModel(application){
    val userRepo = UserRepository()

    val addcomplaintrespResult: MutableLiveData<AddComplaintResponse> = MutableLiveData()
    fun calladdcompaintreqdata(getstatename: String, getdivisionname: String ,getdistricname: String ,getuname: String ,getumobile:String,getumobilealt:String ,getserviceid:String ,getinputmsg:String ,getservicename:String ,getbankname:String ,getcompCat:String ,getcspgoryname:String) {

//            loginResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {

                val addComplaintRequest = AddComplaintReqmodel(
                    state = getstatename,
                    division = getdivisionname,
                    dist = getdistricname,
                    name = getuname,
                    mobile = getumobile,
                    alt_mobile = getumobilealt,
                    serviceId = getserviceid,
                    msg = getinputmsg,
                    serviceName = getservicename,
                    bankName = getbankname,
                    compCat = getcompCat,
                    cspCat = getcspgoryname,

                    )
                val response = userRepo.addcomplainrepo(addComplaintReqmodelrepo = addComplaintRequest)
                if (response?.code() == 200) {
                    addcomplaintrespResult.value=response.body()
                } else {
                    addcomplaintrespResult.value = response!!.body()
                }

            } catch (ex: Exception) {
//                    loginResult.value =ex.message
            }
        }
    }


    val addcomplaintrespResult_other: MutableLiveData<AddComplaintResponse> = MutableLiveData()
    fun calladdcompaintreqdata_otherbank(getstatename: String, getdivisionname: String ,getdistricname: String ,getuname: String ,getumobile:String,getumobile_alternet :String,getserviceid:String ,getinputmsg:String ,getservicename:String ,getbankname:String ,getcategoryname:String ,getcspcategoryname :String ) {
//            loginResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val addComplaintRequest = AddComplaintReqmodel(
                    state = getstatename,
                    division = getdivisionname,
                    dist = getdistricname,
                    name = getuname,
                    mobile = getumobile,
                    alt_mobile = getumobile_alternet,
                    serviceId = getserviceid,
                    msg = getinputmsg,
                    serviceName = getservicename,
                    bankName = getbankname,
                    compCat = getcategoryname,
                    cspCat = getcspcategoryname,
                    )
                val response = userRepo.addcomplainrepo_other(addComplaintReqmodelrepo = addComplaintRequest)
                if (response?.code() == 200) {
                    addcomplaintrespResult_other.value=response.body()
                } else {
                    addcomplaintrespResult_other.value = response!!.body()
                }

            } catch (ex: Exception) {
//                    loginResult.value =ex.message
            }
        }
    }

    val complaint_list_onlysbi: MutableLiveData<CompliantListResposne> = MutableLiveData()
    fun callcomplaintlist_onlysbi(getstartdate: String, gettodate: String ,getusermobile: String ) {
        viewModelScope.launch {
            try {
                val complaintlistrequest = ComplaintReportListRequest(
                    start_date = getstartdate,
                    end_date = gettodate,
                    mobile = getusermobile,
                    )
                val response = userRepo.addcomplainlist_repoonlysbi(complaintlistonlysbi = complaintlistrequest)


                if (response?.code() == 200) {
                    complaint_list_onlysbi.value=response.body()
                } else {
                    complaint_list_onlysbi.value = response!!.body()
                }

            } catch (ex: Exception) {
//                    loginResult.value =ex.message
            }
        }
    }

    val addfeedback_onlysbi: MutableLiveData<AddFeedbackResponse> = MutableLiveData()
    fun calladdfeedback_onlysbi(getkoid: Int, getrating: String ,getfeedbackmsg: String ) {
        viewModelScope.launch {
            try {
                val addfeedback = AddComplaintFeedbackRequest(
                    id = getkoid,
                    rating = getrating,
                    feedback = getfeedbackmsg,
                )
                val response = userRepo.addfeednack_repoonlysbi(addfeedbackreq = addfeedback)

                if (response?.code() == 200) {
                    addfeedback_onlysbi.value=response.body()
                } else {
                    addfeedback_onlysbi.value = response!!.body()
                }

            } catch (ex: Exception) {
//                    loginResult.value =ex.message
            }
        }
    }

    val cspcategory_resultrep: MutableLiveData<GetCspCategoryResponse> = MutableLiveData()

    fun callcspcategory(getkoid: String ) {
        viewModelScope.launch {
            try {
                val cspcategoryreq = GetCspCategoryRequest(
                    koid = getkoid,
                )
                val response = userRepo.cspcategory_repoonlysbi(getcspcategory = cspcategoryreq)

                if (response?.code() == 200) {
                    cspcategory_resultrep.value=response.body()
                } else {
                    cspcategory_resultrep.value = response!!.body()
                }

            } catch (ex: Exception) {
//                    loginResult.value =ex.message
            }
        }
    }


}