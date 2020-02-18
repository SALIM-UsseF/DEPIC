import React from 'react'
import PropTypes from 'prop-types'

import SideBarMenu from './SideBarMenu'
import SideBarHome from './SideBarHome'
import Frame from './Frame'

const styles = {
  frame: {
    paddingTop: "100px",
    paddingLeft: "100px",
    paddingRight: "100px",
    margin: "0 auto",
    maxWidth: "1500px",
    display: "flex",
    flexDirection: "column",
    width: "Auto"
  }
};

export default class View extends React.Component {
  static propTypes = {
    client: PropTypes.any.isRequired,
    lang: PropTypes.string,
    onOpenSondageAgain: PropTypes.func,
    admin: PropTypes.object, // {idAdmin, pseudoAdmin, emailAdmin, supAdmin}
    onDeconnexion: PropTypes.func    
  }

  static defaultProps = {
    lang: 'fr'
  }

  state = {
    openSideBar: false,
    openCompte: false,
    title: 'dashboard',
    openModalSondage: false,
    idSondage: 0,
    nomSondage: '',
    descriptionSondage: ''
  }

  onItemClickSideBarHome = name => {
    if (name === 'home') {
      this.setState({
        openSideBar: true
      });
    } else if (name === 'compte') {
      this.setState({
        openCompte: true
      });
    } else if (name === 'déconnecter') {
      if (this.props.onDeconnexion) {
        this.props.onDeconnexion();
      }
    }
  }

  onHideSideBar = () => {
    this.setState({
      openSideBar: false
    });
  }

  onItemClickSideBarMenu = name => {
    this.setState({
      title: name
    });
  }

  onCreateSurvey = title => {
    this.setState({
      title: title
    })
  }

  onOpenSondage = () => {
    this.setState({
      openModalSondage: true
    })
  }

  openModalSondageFunc = () => {
    this.setState({
      openModalSondage: true
    })
  }

  closeModalSondageFunc = () => {
    this.setState({
      openModalSondage: false,
      title: 'dashboard'
    })
  }

  onSuccessSondage = () => {
    this.setState({
      openModalSondage: false
    });
  }

  render() {
    return (
      <React.Fragment>
        {/* SideBarHome : Barre de menu d'accueil */}
        <SideBarHome
          client={this.props.client}
          onItemClick={this.onItemClickSideBarHome}
        />

        <div style={styles.frame}>
          <Frame
            client={this.props.client}
            lang={this.props.lang}
            title={this.state.title}
            idAdmin={this.props.admin.idAdmin}
            supAdmin={this.props.admin.supAdmin}
            onCreateSurvey={this.onCreateSurvey}
            openModalSondage={this.state.openModalSondage}
            openModalSondageFunc={this.openModalSondageFunc}
            closeModalSondageFunc={this.closeModalSondageFunc}
            onSuccess={this.onSuccessSondage} />
        </div>

        {/* SideBarMenu : Barre de menu de paramètres */}
        <SideBarMenu
          client={this.props.client}
          lang={this.props.lang}
          open={this.state.openSideBar}
          title={this.state.title}
          onHide={this.onHideSideBar}
          onItemClick={this.onItemClickSideBarMenu}
          onOpenSondage={this.onOpenSondage}
        />
      </React.Fragment>
    );
  }
}