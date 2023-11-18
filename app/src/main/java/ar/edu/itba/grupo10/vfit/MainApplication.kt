package ar.edu.itba.grupo10.vfit

import android.app.Application
import ar.edu.itba.grupo10.vfit.data.network.CycleRemoteDataSource
import ar.edu.itba.grupo10.vfit.data.network.ExerciseRemoteDataSource
import ar.edu.itba.grupo10.vfit.data.network.RoutineRemoteDataSource
import ar.edu.itba.grupo10.vfit.data.network.UserRemoteDataSource
import ar.edu.itba.grupo10.vfit.data.network.api.RetrofitClient
import ar.edu.itba.grupo10.vfit.data.repository.CycleRepository
import ar.edu.itba.grupo10.vfit.data.repository.ExerciseRepository
import ar.edu.itba.grupo10.vfit.data.repository.RoutineRepository
import ar.edu.itba.grupo10.vfit.data.repository.UserRepository
import ar.edu.itba.grupo10.vfit.utils.SessionManager

class MainApplication : Application() {

    private val userRemoteDataSource: UserRemoteDataSource
        get() = UserRemoteDataSource(sessionManager, RetrofitClient.getApiUserService(this))

    private val routineRemoteDataSource: RoutineRemoteDataSource
        get() = RoutineRemoteDataSource(RetrofitClient.getApiRoutineService(this))

    private val cycleRemoteDataSource: CycleRemoteDataSource
        get() = CycleRemoteDataSource(RetrofitClient.getApiCycleService(this))

    private val exerciseRemoteDataSource: ExerciseRemoteDataSource
        get() = ExerciseRemoteDataSource(RetrofitClient.getApiExerciseService(this))

    val sessionManager: SessionManager
        get() = SessionManager(this)

    val userRepository: UserRepository
        get() = UserRepository(userRemoteDataSource)

    val routineRepository: RoutineRepository
        get() = RoutineRepository(routineRemoteDataSource)

    val cycleRepository: CycleRepository
        get() = CycleRepository(cycleRemoteDataSource)

    val exerciseRepository: ExerciseRepository
        get() = ExerciseRepository(exerciseRemoteDataSource)

}