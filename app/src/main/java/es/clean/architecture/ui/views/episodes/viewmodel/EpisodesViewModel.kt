package es.clean.architecture.ui.views.episodes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import es.clean.architecture.domain.characters.models.episodes.RickyMortyEpisodesModel
import es.clean.architecture.domain.characters.repository.episodes.AllEpisodesUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
open class EpisodesViewModel @Inject constructor(
    allEpisodesUseCase: AllEpisodesUseCase
) : ViewModel() {
    val allEpisodes: Flow<PagingData<RickyMortyEpisodesModel.Episode>> = allEpisodesUseCase(20)

}