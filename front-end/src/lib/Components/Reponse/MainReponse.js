import React from 'react'
import PropTypes from 'prop-types'
import _ from 'lodash'

import {
  Message,
  Segment
} from 'semantic-ui-react'

import Title from '../Title'
import ReponseChoix from './ReponseChoix'
import ReponseOuverte from './ReponseOuverte'
import GroupeReponse from './GroupeReponse'
import ReponsePoints from './ReponsePoints'

//import { dictionnary } from '../../Langs/langs'

const styles = {
  frame: {
    paddingTop: "100px",
    paddingLeft: "100px",
    paddingRight: "100px",
    margin: "0 auto",
    marginBottom: "30px",
    maxWidth: "1500px",
    display: "flex",
    flexDirection: "column",
    width: "Auto"
  },
  titleQuestion: {
    fontSize: "25px",
    marginTop: "40px",
    marginBottom: "25px"
  }
};

export default class MainReponse extends React.Component {
  static propTypes = {
    client: PropTypes.any.isRequired,
    lang: PropTypes.string,
    idSondage: PropTypes.number
  }

  static defaultProps = {
    lang: 'fr'
  }

  state = {
    existSondage: false,
    title: '',
    description: '',
    questions: []
  }

  componentDidMount() {
    this.props.client.Sondage.read(
      this.props.idSondage,
      result => {
        this.setState({
          existSondage: true,
          title: result.data.intituleSondage,
          description: result.data.descriptionSondage
        });

        this.props.client.Question.readBySondage(
          this.props.idSondage,
          result => {
            this.setState({
              questions: result.data
            });
          },
          error => {
            this.setState({
              questions: []
            });
          }
        )
      },
      error => {
        console.log(error);

        this.setState({
          existSondage: false
        });
      }
    )
  }

  render() {
    let key = 0;

    return (
      <React.Fragment>
        <div style={styles.frame}>
          {this.state.existSondage?
            <React.Fragment>
              <Title
                as='h1'
                content={this.state.title}
                color='teal' />
            
              <Segment
                color='teal'
                size='large'
              >{this.state.description}</Segment>

              {_.map(this.state.questions, question => {
                key++;

                if (question.type === 'QuestionChoix') {
                  return (               
                    <ReponseChoix
                      key={key} 
                      client={this.props.client}
                      obligatoire={question.estObligatoire}
                      unique={question.estUnique}
                      idQuestion={question.id_question}
                      intitule={question.intitule}
                      nbChoix={question.nombreChoix}
                      ordre={key}
                      styles={styles} />
                  )
                } else if (question.type === 'QuestionOuverte') {
                  return (
                    <ReponseOuverte
                      key={key}
                      client={this.props.client}
                      obligatoire={question.estObligatoire}
                      idQuestion={question.id_question}
                      intitule={question.intitule}
                      nombreDeCaractere={question.nombreDeCaractere}
                      ordre={key}
                      styles={styles} />
                  );
                } else if (question.type === 'GroupeQuestion') {
                  return (
                    <GroupeReponse
                      key={key}
                      client={this.props.client}
                      obligatoire={question.estObligatoire}
                      idQuestion={question.id_question}
                      intitule={question.intitule}
                      ordre={key}
                      styles={styles} />
                  );
                } else if (question.type === 'QuestionPoint') {
                  return (
                    <ReponsePoints
                      key={key}
                      client={this.props.client}
                      obligatoire={question.estObligatoire}
                      idQuestion={question.id_question}
                      intitule={question.intitule}
                      maxPoints={question.maxPoints}
                      minPoints={question.minPoints}
                      ordre={key}
                      styles={styles} />
                  );
                }
              })}
            </React.Fragment>
            :
            <React.Fragment>
              <Message 
                negative
                size='huge'
              >
                <Message.Header>Le sondage n°{this.props.idSondage} n'existe pas</Message.Header>
                <p>Veuillez renseigner un numéro de sondage valide</p>
              </Message>
            </React.Fragment>
          }
        </div>
      </React.Fragment>
    );
  }
}