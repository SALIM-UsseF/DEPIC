import React from 'react'

import { MainUser } from './User'
import { View } from './View'

export default class MainView extends React.Component {
  state = {
    page: 'mainView'
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
              <View />
              :''
        }
      </React.Fragment>
    );
  }
}