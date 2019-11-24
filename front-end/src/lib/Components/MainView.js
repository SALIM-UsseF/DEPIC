import React from 'react'

import { MainUser } from './User'
import SettingsView from './SettingsView'

export default class MainView extends React.Component {
  state = {
    page: 'connexion'
  }

  onSuccess = () => {
    this.setState({
      page: 'mainView'
    })
  }

  render() {
    return (
      <React.Fragment>
        {(this.state.page === 'connexion')?
            <MainUser onSuccess={this.onSuccess}/>
            :(this.state.page === 'mainView')?
              <SettingsView />
              :''
        }
      </React.Fragment>
    );
  }
}