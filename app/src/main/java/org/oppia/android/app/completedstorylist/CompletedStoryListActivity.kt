package org.oppia.android.app.completedstorylist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import org.oppia.android.app.activity.ActivityComponentImpl
import org.oppia.android.app.activity.InjectableAutoLocalizedAppCompatActivity
import org.oppia.android.app.model.CompletedStoryListActivityArguments
import org.oppia.android.app.model.ScreenName.COMPLETED_STORY_LIST_ACTIVITY
import org.oppia.android.util.extensions.getProtoExtra
import org.oppia.android.util.extensions.putProtoExtra
import org.oppia.android.util.logging.CurrentAppScreenNameIntentDecorator.decorateWithScreenName
import javax.inject.Inject

/** The arguments key  for [CompletedStoryListActivity]. */

/** Activity for completed stories. */
class CompletedStoryListActivity : InjectableAutoLocalizedAppCompatActivity() {
  @Inject
  lateinit var completedStoryListActivityPresenter: CompletedStoryListActivityPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    (activityComponent as ActivityComponentImpl).inject(this)

    val args = intent.getProtoExtra(
      COMPLETED_STORY_LIST_ACTIVITY_ARGUMENTS_KEY,
      CompletedStoryListActivityArguments.getDefaultInstance()
    )

    val internalProfileId: Int = args?.internalProfileId ?: -1
    completedStoryListActivityPresenter.handleOnCreate(internalProfileId)
  }

  companion object {
    // TODO(#1655): Re-restrict access to fields in tests post-Gradle.
    const val COMPLETED_STORY_LIST_ACTIVITY_ARGUMENTS_KEY = "CompletedStoryListActivity.arguments"

    /** Returns a new [Intent] to route to [CompletedStoryListActivity] for a specified profile ID. */
    fun createCompletedStoryListActivityIntent(context: Context, internalProfileId: Int): Intent {
      val intent = Intent(context, CompletedStoryListActivity::class.java)
      val args =
        CompletedStoryListActivityArguments.newBuilder().setInternalProfileId(internalProfileId)
          .build()
      intent.putProtoExtra(COMPLETED_STORY_LIST_ACTIVITY_ARGUMENTS_KEY, args)
      intent.decorateWithScreenName(COMPLETED_STORY_LIST_ACTIVITY)
      return intent
    }
  }
}
