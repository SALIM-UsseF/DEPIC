import React from 'react'

import Login from './Login'

export default class MainAdministration extends React.Component {
  onSuccess = msg => {
    console.log(msg);
  }

  render() {
    return (
      <React.Fragment>
        <Login onSuccess={this.onSuccess} />
      </React.Fragment>
    );
  }
}