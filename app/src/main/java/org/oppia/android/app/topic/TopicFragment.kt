package org.oppia.android.app.topic

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.oppia.android.app.fragment.FragmentComponentImpl
import org.oppia.android.app.fragment.InjectableFragment
import org.oppia.android.app.model.TopicFragmentArguments
import org.oppia.android.domain.topic.TEST_TOPIC_ID_0
import org.oppia.android.util.extensions.getProto
import javax.inject.Inject

/** Fragment that contains tabs for Topic. */
class TopicFragment : InjectableFragment() {
  @Inject
  lateinit var topicFragmentPresenter: TopicFragmentPresenter

  override fun onAttach(context: Context) {
    super.onAttach(context)
    (fragmentComponent as FragmentComponentImpl).inject(this)
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    val args = arguments?.getProto(
      TOPIC_FRAGMENT_ARGUMENTS_KEY,
      TopicFragmentArguments.getDefaultInstance()
    )
    val internalProfileId = args?.profileId ?: -1
    val topicId = args?.topicId ?: TEST_TOPIC_ID_0
    val storyId = args?.storyId ?: ""

    return topicFragmentPresenter.handleCreateView(
      inflater,
      container,
      internalProfileId,
      topicId,
      storyId,
      isConfigChanged = savedInstanceState != null
    )
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    topicFragmentPresenter.startSpotlight()
  }
}
