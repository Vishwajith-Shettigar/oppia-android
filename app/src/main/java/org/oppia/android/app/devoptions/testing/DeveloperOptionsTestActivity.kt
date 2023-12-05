package org.oppia.android.app.devoptions.testing

import android.content.Context
import android.content.Intent
import android.os.Bundle
import org.oppia.android.R
import org.oppia.android.app.activity.ActivityComponentImpl
import org.oppia.android.app.activity.InjectableAutoLocalizedAppCompatActivity
import org.oppia.android.app.devoptions.DeveloperOptionsActivity
import org.oppia.android.app.devoptions.DeveloperOptionsFragment
import org.oppia.android.app.devoptions.ForceCrashButtonClickListener
import org.oppia.android.app.devoptions.RouteToMarkChaptersCompletedListener
import org.oppia.android.app.devoptions.RouteToMarkStoriesCompletedListener
import org.oppia.android.app.devoptions.RouteToMarkTopicsCompletedListener
import org.oppia.android.app.devoptions.RouteToViewEventLogsListener
import org.oppia.android.app.devoptions.markchapterscompleted.MarkChaptersCompletedActivity
import org.oppia.android.app.devoptions.markstoriescompleted.MarkStoriesCompletedActivity
import org.oppia.android.app.devoptions.marktopicscompleted.MarkTopicsCompletedActivity
import org.oppia.android.app.devoptions.vieweventlogs.ViewEventLogsActivity
import org.oppia.android.app.model.DeveloperOptionsTestActivityArguments
import org.oppia.android.util.extensions.getProtoExtra
import org.oppia.android.util.extensions.putProtoExtra

/** Activity for testing [DeveloperOptionsFragment]. */
class DeveloperOptionsTestActivity :
  InjectableAutoLocalizedAppCompatActivity(),
  ForceCrashButtonClickListener,
  RouteToMarkChaptersCompletedListener,
  RouteToMarkStoriesCompletedListener,
  RouteToMarkTopicsCompletedListener,
  RouteToViewEventLogsListener {

  private var internalProfileId = -1

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    (activityComponent as ActivityComponentImpl).inject(this)
    setContentView(R.layout.developer_options_activity)
    val args = intent.getProtoExtra(
      DEVELOPER_OPTIONS_TEST_ACTIVITY_ARGUMENTS_KEY,
      DeveloperOptionsTestActivityArguments.getDefaultInstance()
    )
    internalProfileId = args?.profileId ?: -1
    if (getDeveloperOptionsFragment() == null) {
      supportFragmentManager.beginTransaction().add(
        R.id.developer_options_fragment_placeholder,
        DeveloperOptionsFragment.newInstance()
      ).commitNow()
    }
  }

  private fun getDeveloperOptionsFragment(): DeveloperOptionsFragment? {
    return supportFragmentManager.findFragmentById(
      R.id.developer_options_fragment_placeholder
    ) as DeveloperOptionsFragment?
  }

  override fun routeToMarkChaptersCompleted() {
    startActivity(
      MarkChaptersCompletedActivity.createMarkChaptersCompletedIntent(
        context = this, internalProfileId, showConfirmationNotice = false
      )
    )
  }

  override fun routeToMarkStoriesCompleted() {
    startActivity(
      MarkStoriesCompletedActivity
        .createMarkStoriesCompletedIntent(this, internalProfileId)
    )
  }

  override fun routeToMarkTopicsCompleted() {
    startActivity(
      MarkTopicsCompletedActivity
        .createMarkTopicsCompletedIntent(this, internalProfileId)
    )
  }

  override fun routeToViewEventLogs() {
    startActivity(ViewEventLogsActivity.createViewEventLogsActivityIntent(this))
  }

  override fun forceCrash() {
    throw RuntimeException("Force crash occurred")
  }

  companion object {
    /** Argument key for DeveloperOptionsTestActivity. */
    const val DEVELOPER_OPTIONS_TEST_ACTIVITY_ARGUMENTS_KEY = "DeveloperOptionsTestActivity.arguments"

    /** Returns [Intent] for [DeveloperOptionsTestActivity]. */
    fun createDeveloperOptionsTestIntent(context: Context, internalProfileId: Int): Intent {
      val intent = Intent(context, DeveloperOptionsActivity::class.java)
        .apply {
          val args = DeveloperOptionsTestActivityArguments.newBuilder().apply {
            profileId = internalProfileId
          }.build()
          putProtoExtra(DEVELOPER_OPTIONS_TEST_ACTIVITY_ARGUMENTS_KEY, args)
        }
      return intent
    }
  }
}
