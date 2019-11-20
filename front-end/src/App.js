import React from 'react'

import MainAdministration from './Components/Administration/MainAdministration'

export default class App extends React.Component {
  onSuccess = msg => {
    console.log(msg);
  }

  render() {
    return (
      <div>
        <MainAdministration />
      </div>
    );
  }
}