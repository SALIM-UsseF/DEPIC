import React from 'react'

import { MainUser } from './User'
import { View } from './View'

export default class MainView extends React.Component {
  state = {
    page: 'connexion',
    lang: 'fr'
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
            <MainUser
              lang={this.state.lang}
              onSuccess={this.onSuccess} />
            :(this.state.page === 'mainView')?
              <View
                lang={this.state.lang} />
              :''
        }
      </React.Fragment>
    );
  }
}