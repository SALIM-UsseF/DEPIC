import React from 'react'
import './App.css'

import MainAdministration from './Components/Administration/MainAdministration'

export default class App extends React.Component {
  onSuccess = msg => {
    console.log(msg);
  }

  render() {
    return (
      <div>
        <div className='App-header'>
          <MainAdministration />
        </div>
      </div>
    );
  }
}