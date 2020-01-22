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
    page: 'mainReponse',
    lang: this.props.lang
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