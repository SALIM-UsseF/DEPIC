import React from 'react'

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

export default class MainAdministration extends React.Component {
  state = {
    page: 'connexion'
  }

  onSuccessConnexion = msg => {
    console.log(msg);
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