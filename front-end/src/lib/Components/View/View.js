import React from 'react'

import SideBarMenu from './SideBarMenu'
import SideBarHome from './SideBarHome'

export default class View extends React.Component {
  state = {
    openSideBar: false
  }

  onItemClickSideBarHome = name => {
    if (name === 'home') {
      this.setState({
        openSideBar: true
      });
    } else if (name === 'user') {
      console.log('OK')
    }
  }

  onHideSideBar = () => {
    this.setState({
      openSideBar: false
    });
  }

  onItemClickSideBarMenu = name => {
    console.log(name);
  }

  render() {
    return (
      <React.Fragment>
        {/* SideBarHome : Barre de menu d'accueil */}
        <SideBarHome 
          onItemClick={this.onItemClickSideBarHome}
        />

        {/* SideBarMenu : Barre de menu de paramètres */}
        <SideBarMenu 
          open={this.state.openSideBar}
          onHide={this.onHideSideBar}
          onItemClick={this.onItemClickSideBarMenu}
        />
      </React.Fragment>
    );
  }
}