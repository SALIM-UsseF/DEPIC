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
  onSuccess = msg => {
    console.log(msg);
  }

  render() {
    return (
      <React.Fragment>
        <div style={styles.connexion}>
          <Login onSuccess={this.onSuccess} />
          <Register />
        </div>
      </React.Fragment>
    );
  }
}