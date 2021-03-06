import React from 'react'
import PropTypes from 'prop-types'

import Login from './Login'
import Register from './Register'

const styles = {
  connexion: {
    paddingTop: "200px",
    paddingLeft: "10px",
    paddingRight: "10px",
    margin: "0 auto",
    maxWidth: "600px",
    display: "flex",
    flexDirection: "column",
    width: "Auto"
  },
  register: {
    paddingTop: "150px",
    paddingLeft: "10px",
    paddingRight: "10px",
    margin: "0 auto",
    maxWidth: "600px",
    display: "flex",
    flexDirection: "column",
    width: "Auto"
  }
};

export default class MainUser extends React.Component {
  static propTypes = {
    client: PropTypes.any.isRequired,
    lang: PropTypes.string,
    onSuccess: PropTypes.func
  }

  static defaultProps = {
    lang: 'fr'
  }

  state = {
    page: 'connexion'
  }

  onSuccessConnexion = (admin) => {
    if (this.props.onSuccess) {
      this.props.onSuccess(admin);
    }
  }

  onSuccessRegister = () => {
    this.setState({
      page: 'connexion'
    });
  }

  createAccount = () => {
    this.setState({
      page: 'register'
    });
  }

  render() {
    return (
      <React.Fragment>
        {this.state.page === 'connexion'?
          <div style={styles.connexion}>
            <Login
              client={this.props.client}
              lang={this.props.lang}
              onSuccess={this.onSuccessConnexion}
              createAccount={this.createAccount}
            />
          </div>
          : this.state.page === 'register'?
            <div style={styles.register}>
              <Register
                client={this.props.client}
                lang={this.props.lang}
                onSuccess={this.onSuccessRegister}
                onCancel={this.onSuccessRegister}
              />
            </div>
            :''
        }
      </React.Fragment>
    );
  }
}