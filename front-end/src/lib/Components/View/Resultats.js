import React from 'react'
import PropTypes from 'prop-types'
import { Form, Header, Divider } from 'semantic-ui-react'
// import _ from 'lodash'

// import { dictionnary } from '../../Langs/langs'

export default class Resultats extends React.Component {
  static propTypes = {
    client: PropTypes.any.isRequired,
    lang: PropTypes.string,
    idSondage: PropTypes.number
  }

  static defaultProps = {
    lang: 'fr'
  }

  state = {
    resultat: {}
  }

  componentDidMount() {
    this.props.client.Resultat.read(
      this.props.idSondage,
      result => {
        this.setState({
          resultat: result.data
        });
      },
      error => {
        console.log(error);
      }
    )
  }

  render() {
    console.log(this.state.resultat);
    return (
      <React.Fragment>
        <Divider hidden />
        <Form>
          <Header as='h2'>Nombre de participations : </Header>
          <Header as='h2'>Moyennes générales des questions à points : </Header>
          <Header as='h2'>Nombre de participations sur chaque choix des questions à choix unique</Header>
          <Header as='h2'>Nombre de participations sur chaque choix des questions à choix multiple</Header>
        </Form>
      </React.Fragment>
    );
  }
}