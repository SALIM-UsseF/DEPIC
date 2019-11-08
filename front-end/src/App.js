import React from 'react'
import './App.css'

import Question from './components/Question'

export default class App extends React.Component {
  render() {
    return (
      <div>
        <div className='App-header'>
          <Question />
        </div>
      </div>
    );
  }
}