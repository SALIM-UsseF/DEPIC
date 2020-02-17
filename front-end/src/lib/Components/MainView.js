import React from 'react'
import PropTypes from 'prop-types'

import { MainUser } from './User'
import { View } from './View'
import { MainReponse } from './Reponse'

export default class MainView extends React.Component {
  static propTypes = {
    client: PropTypes.any.isRequired,
    emailSuperAdmin: PropTypes.string,
    passwordSuperAdmin: PropTypes.string,
    lang: PropTypes.string
  }

  static defaultProps = {
    lang: 'fr'
  }

  state = {
    page: 'connexion',
    lang: this.props.lang,
    admin: {}
  }

  onSuccess = (admin) => {
    this.setState({
      page: 'mainView',
      admin: admin
    });
  }

  onDeconnexion = () => {
    this.setState({
      page: 'connexion',
      admin: {}
    });
  }

  render() {
    return (
      <React.Fragment>
        {(this.state.page === 'connexion')?
            <MainUser
              client={this.props.client}
              lang={this.state.lang}
              onSuccess={this.onSuccess} />
            :(this.state.page === 'mainView')?
              <View
                client={this.props.client}
                lang={this.state.lang}
                admin={this.state.admin}
                onDeconnexion={this.onDeconnexion} />
              :(this.state.page === 'mainReponse')?
                <MainReponse
                  client={this.props.client}
                  idSondage={1}
                />
                :''
        }
      </React.Fragment>
    );
  }
}