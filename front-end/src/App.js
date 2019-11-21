import React from 'react'

import {
  MainUser
} from './lib'

export default class App extends React.Component {
  onSuccess = msg => {
    console.log(msg);
  }

  render() {
    return (
      <div>
        <MainUser />
      </div>
    );
  }
}