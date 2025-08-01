package com.example.multiplatform_paging_sample

import app.cash.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable

typealias OnExploreItemClicked = (Repository) -> Unit
sealed interface Event {

    data class SearchTerm(
        val searchTerm: String,
    ) : Event
}

sealed interface ViewModel {

    data object Empty : ViewModel

    data class SearchResults(
        val searchTerm: String,
        val repositories: Flow<PagingData<Repository>>,
    ) : ViewModel
}


@Serializable
data class  Owner(
    val login :String,
    val id: Long,
    val node_id :String,
    val avatar_url :String,
    val gravatar_id :String?,
    val url :String,
    val html_url :String,
    val followers_url :String,
    val following_url :String,
    val gists_url :String,
    val starred_url :String,
    val subscriptions_url :String,
    val organizations_url :String,
    val repos_url :String,
    val events_url :String,
    val received_events_url :String,
    val type :String,
    val user_view_type :String,
    val site_admin: Boolean
)

@Serializable
data class License(
    val key: String,
    val name: String,
    val spdx_id: String,
    val url: String,
    val node_id: String
)

@Serializable
data class Repository(

    val full_name :String,
    val stargazers_count: Int,
    val id: Long,
    val name :String,
    val html_url :String,
    val forks_count: Int,
    val language :String?,
    val description :String?,

    /*
    val node_id :String,
    val private: Boolean,
    val owner:Owner,
    val fork: Boolean,
    val url :String,
    val forks_url :String,
    val keys_url :String,
    val collaborators_url :String,
    val teams_url :String,
    val hooks_url :String,
    val issue_events_url :String,
    val events_url :String,
    val assignees_url :String,
    val branches_url :String,
    val tags_url :String,
    val blobs_url :String,
    val git_tags_url :String,
    val git_refs_url :String,
    val trees_url :String,
    val statuses_url :String,
    val languages_url :String,
    val stargazers_url :String,
    val contributors_url :String,
    val subscribers_url :String,
    val subscription_url :String,
    val commits_url :String,
    val git_commits_url :String,
    val comments_url :String,
    val issue_comment_url :String,
    val contents_url :String,
    val compare_url :String,
    val merges_url :String,
    val archive_url :String,
    val downloads_url :String,
    val issues_url :String,
    val pulls_url :String,
    val milestones_url :String,
    val notifications_url :String,
    val labels_url :String,
    val releases_url :String,
    val deployments_url :String,
    val created_at :String,
    val updated_at :String,
    val pushed_at :String,
    val git_url :String,
    val ssh_url :String,
    val clone_url :String,
    val svn_url :String,
    val homepage :String?,
    val size: Int,
    val watchers_count: Int,
    val language :String?,
    val has_issues: Boolean,
    val has_projects: Boolean,
    val has_downloads: Boolean,
    val has_wiki: Boolean,
    val has_pages: Boolean,
    val has_discussions: Boolean,
    val forks_count: Int,
    val mirror_url: String?,
    val archived: Boolean,
    val disabled: Boolean,
    val open_issues_count: Int,
    val license: License?,
    val allow_forking: Boolean,
    val is_template: Boolean,
    val web_commit_signoff_required: Boolean,
    val topics: List<String>,
    val visibility: String,
    val forks: Int,
    val open_issues: Int,
    val watchers: Int,
    val default_branch: String,
    val score: Float

     */
)

@Serializable
data class Repositories(
    val total_count: Int,
    val incomplete_results: Boolean,
    val items: List<Repository>
)

@Serializable
data class SharedMarker(
    val latitude: Double,
    val longitude: Double,
    val snippet: String,
    val title: String,
)
