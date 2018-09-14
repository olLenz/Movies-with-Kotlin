FROM circleci/android:api-28-alpha

RUN gem install fastlane

ENV ANDROID_HOME /opt/android/sdk

COPY --chown=circleci:circleci . /home/circleci/src

RUN cd /home/circleci/src/ \
    && fastlane deploy \