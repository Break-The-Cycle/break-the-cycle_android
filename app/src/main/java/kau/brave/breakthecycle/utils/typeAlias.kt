package kau.brave.breakthecycle.utils

import kau.brave.breakthecycle.data.response.BraveResponse
import kau.brave.breakthecycle.network.model.ApiState


typealias ApiWrapper<T> = ApiState<BraveResponse<T>>
