import {Alert, Button, Card, Container, Form, FormGroup, Spinner} from "react-bootstrap";
import {useAuthContext} from "../contexts/AuthContext.tsx";
import {useNotificationContext} from "../contexts/NotificationContext.tsx";
import {useNavigate} from "react-router-dom";
import * as Yup from 'yup';
import {object} from "yup";
import axios from "axios";
import {Formik} from "formik";
import {NotificationVariants} from "../NotificationVariants.ts";
import {RegistrationRequest} from "../models/RegisterRequest.ts";

export default function Register(){
    const {updateToken} = useAuthContext();
    const {pushNotification} = useNotificationContext();
    const navigate = useNavigate();

    const registrationSchema = object({
        username: Yup.string()
            .min(6, 'Username must have at least 6 characters')
            .required("Username is required"),
        password: Yup.string()
            .required("Password is required"),
        passwordConfirm: Yup.string()
            .oneOf([Yup.ref('password')], "Passwords don't match!")
            .required("Password confirm is required")
    });

    function submitRegistration(values: RegistrationRequest, setSubmitting: (isSubmitting: boolean) => void){
        console.log(values);

        axios.post("http://localhost:8080/api/auth/register", {
            username: values.username,
            password: values.password
        })
            .then(res => {
                const accessToken = res.data.token;
                updateToken(accessToken);
                setSubmitting(false);
                pushNotification("Succesfully created account!", NotificationVariants.success);
                navigate('/');
            })
            .catch(err => {
                console.error(err);
                setSubmitting(false);
                const message = err.response?.status === 400 ?
                    'User with this username already exists' : 'Error during sign up';

                pushNotification(message, NotificationVariants.danger);
            });
    }

    return (
        <>
            <Container className="d-flex justify-content-center align-items-center" style={{ height: '90vh' }}>
                <Card style={{ width: '50rem', height: '80%'}}>
                    <Card.Body style={{display: 'flex', flexDirection: 'column', justifyContent: 'center' }}>
                        <Card.Title className="text-center">Create new account</Card.Title>
                        <Formik initialValues={{username: '', password: '', passwordConfirm: ''}}
                                onSubmit={(values: RegistrationRequest, {setSubmitting}) => submitRegistration(values, setSubmitting)}
                                validationSchema={registrationSchema}>
                            {({errors, isSubmitting, isValid, dirty, handleSubmit, handleChange, handleBlur, touched}) => (
                                <Form onSubmit={handleSubmit} noValidate>
                                    <FormGroup controlId="username" className="mb-2">
                                        <Form.Label>Username</Form.Label>
                                        <Form.Control type="text" name="username" placeholder="Enter your username" onChange={handleChange} onBlur={handleBlur}/>
                                        {touched.username && errors.username && <Alert style={{marginTop: '4px'}} variant="danger">{errors.username}</Alert>}
                                    </FormGroup>
                                    <FormGroup controlId="password" className="mb-2">
                                        <Form.Label>Password</Form.Label>
                                        <Form.Control type="password" name="password" placeholder="Enter your Password" onChange={handleChange} onBlur={handleBlur}/>
                                        {touched.password && errors.password && <Alert style={{marginTop: '4px'}} variant="danger">{errors.password}</Alert>}
                                    </FormGroup>
                                    <FormGroup controlId="passwordConfirm" className="mb-2">
                                        <Form.Label>Confirm password</Form.Label>
                                        <Form.Control type="password" name="passwordConfirm" placeholder="Confirm your password" onChange={handleChange} onBlur={handleBlur}/>
                                        {touched.passwordConfirm && errors.passwordConfirm && <Alert style={{marginTop: '4px'}} variant="danger">{errors.passwordConfirm}</Alert>}
                                    </FormGroup>
                                    { isSubmitting ?
                                        <Button variant="primary" disabled>
                                            <Spinner as="span" animation="border" size="sm" role="status" aria-hidden="true"/> Loading...
                                        </Button> :
                                        <Button type="submit" disabled={isSubmitting || !isValid || !dirty}>Register</Button>
                                    }
                                </Form>
                            )}
                        </Formik>
                    </Card.Body>
                </Card>
            </Container>
        </>
    );
}
