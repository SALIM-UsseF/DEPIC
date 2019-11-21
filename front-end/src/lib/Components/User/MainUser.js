import React from 'react'
import PropTypes from 'prop-types'

import Login from './Login'
import Register from './Register'

const styles = {
  connexion: {
    paddingTop: "20px",
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
    onSuccess: PropTypes.func
  }

  state = {
    page: 'connexion'
  }

  onSuccessConnexion = msg => {
    console.log(msg);

    if (this.props.onSuccess) {
      this.props.onSuccess();
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
        <div style={styles.connexion}>
          {this.state.page === 'connexion'?
            <Login 
              onSuccess={this.onSuccessConnexion}
              createAccount={this.createAccount}
            />
            : this.state.page === 'register'?
              <Register 
                onSuccess={this.onSuccessRegister}
                onCancel={this.onSuccessRegister}
              />
              :''
          }
        </div>
      </React.Fragment>
    );
  }
}