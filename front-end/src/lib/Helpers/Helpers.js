const emailValidityForm = email => {
  let regex = /\S+@\S+\.\S+/;
  return (regex.test(email));
}

export {
  emailValidityForm
}