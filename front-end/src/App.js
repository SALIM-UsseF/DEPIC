import React from 'react'
import './App.css'

import CreationQuestions from './components/CreationQuestions'

export default class App extends React.Component {
  render() {
    return (
      <div>
        <div className='App-header'>
          <CreationQuestions />
        </div>
      </div>
    );
  }
}