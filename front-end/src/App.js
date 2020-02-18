import React from 'react'

import {
  Client,
  MainView 
} from './lib'
import { url, emailSuperAdmin, passwordSuperAdmin } from './lib/Helpers/Settings'

export default class App extends React.Component {
  state = {
    client: new Client(url, '')
  }

  componentDidMount() {
    this.state.client.AuthApi.authenticate(
      {
        email: emailSuperAdmin,
        password: passwordSuperAdmin
      },
      result => {
        this.setState({
          client: new Client(url, result.data.auth_token)
        });
      },
      error => {
        console.log(error);
      }
    )
  }

  render() {
    return (
      <div>
        <MainView
          client={this.state.client}
          emailSuperAdmin={emailSuperAdmin}
          passwordSuperAdmin={passwordSuperAdmin}
          lang='fr'
        />
      </div>
    );
  }
}