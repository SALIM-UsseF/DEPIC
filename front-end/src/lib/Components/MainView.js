import React from 'react'
import PropTypes from 'prop-types'

import { MainUser } from './User'
import { View } from './View'
import { MainReponse } from './Reponse'

export default class MainView extends React.Component {
  static propTypes = {
    client: PropTypes.any.isRequired,
    lang: PropTypes.string
  }

  static defaultProps = {
    lang: 'fr'
  }

  state = {
    page: 'mainView',
    lang: this.props.lang,
    idAdmin: 0
  }

  onSuccess = (idAdmin) => {
    this.setState({
      page: 'mainView',
      idAdmin: idAdmin
    })
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
                idAdmin={this.state.idAdmin} />
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