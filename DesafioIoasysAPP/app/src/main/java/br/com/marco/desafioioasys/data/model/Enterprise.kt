package br.com.marco.desafioioasys.data.model

data class Enterprise(
    val city: String,
    val country: String,
    val description: String,
    val email_enterprise: Any,
    val enterprise_name: String,
    val enterprise_type: EnterpriseType,
    val facebook: Any,
    val id: Int,
    val linkedin: Any,
    val own_enterprise: Boolean,
    val phone: Any,
    val photo: String,
    val share_price: Double,
    val twitter: Any,
    val value: Int
)