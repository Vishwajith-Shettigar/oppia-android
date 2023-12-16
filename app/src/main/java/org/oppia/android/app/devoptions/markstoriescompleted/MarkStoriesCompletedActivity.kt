package org.oppia.android.app.devoptions.markstoriescompleted

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import org.oppia.android.R
import org.oppia.android.app.activity.ActivityComponentImpl
import org.oppia.android.app.activity.InjectableAutoLocalizedAppCompatActivity
import org.oppia.android.app.model.MarkStoriesCompletedActivityArguments
import org.oppia.android.app.model.ScreenName.MARK_STORIES_COMPLETED_ACTIVITY
import org.oppia.android.app.translation.AppLanguageResourceHandler
import org.oppia.android.util.extensions.getProtoExtra
import org.oppia.android.util.extensions.putProtoExtra
import org.oppia.android.util.logging.CurrentAppScreenNameIntentDecorator.decorateWithScreenName
import javax.inject.Inject
import org.oppia.android.app.model.ProfileId
import org.oppia.android.util.profile.CurrentUserProfileIdIntentDecorator.decorateWithUserProfileId
import org.oppia.android.util.profile.CurrentUserProfileIdIntentDecorator.extractCurrentUserProfileId

/** Activity for Mark Stories Completed. */
class MarkStoriesCompletedActivity : InjectableAutoLocalizedAppCompatActivity() {

  @Inject
  lateinit var markStoriesCompletedActivityPresenter: MarkStoriesCompletedActivityPresenter

  @Inject
  lateinit var resourceHandler: AppLanguageResourceHandler

  private var internalProfileId = -1

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    (activityComponent as ActivityComponentImpl).inject(this)

    val profileId = intent?.extractCurrentUserProfileId()
    internalProfileId = profileId?.internalId ?: -1
    markStoriesCompletedActivityPresenter.handleOnCreate(internalProfileId)
    title = resourceHandler.getStringInLocale(R.string.mark_stories_completed_activity_title)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == android.R.id.home) {
      onBackPressed()
    }
    return super.onOptionsItemSelected(item)
  }

  companion object {

    /** Returns an [Intent] to start this activity. */
    fun createMarkStoriesCompletedIntent(context: Context, internalProfileId: Int): Intent {
      val profileId = ProfileId.newBuilder().setInternalId(internalProfileId).build()
      return Intent(context, MarkStoriesCompletedActivity::class.java).apply {
        decorateWithUserProfileId(profileId)
        decorateWithScreenName(MARK_STORIES_COMPLETED_ACTIVITY)
      }
    }
  }
}
