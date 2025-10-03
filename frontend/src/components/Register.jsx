import React from "react";
import registerStyles from "../assets/styles/register-styles"; 

const Register = () => {

    const submitRegistration = async (e) => {
        e.preventDefault(); // Prevent default form submission

        const formElement = e.target; // The form element
        const baseUrl = "http://4.156.243.142";
        const url = `${baseUrl}/api/v1/auth/register`; 

        try {
            const formData = new FormData(formElement);  // Turn the data from the form into form data
            const data = Object.fromEntries(formData.entries());  // Turn the form data into a javascript object

            if (data.password !== data.confirm_password) {
                alert("Passwords do not match!");
                return;
            }
            
            const { confirm_password, ...cleanedData} = data;
            const payload = {
                ...cleanedData,
                roles: ["ROLE_USER"]
            }

            const response = await fetch(url, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(payload)
            });
            
            let result = null;

            const text = await response.text();
            if (text) {
                result = JSON.parse(text);
    }
            if (response.ok) {
                alert('Registration successful!');
                formElement.reset(); // Clear the form
                window.location.href = "http://localhost:5173/";  // TODO: change this to frontend url
            } else {
                alert('Error: ' + result.message);
            }

        } catch (err) {
            console.error("Error..:", err);
            alert("An error occurred. Try again");
        }
    };

    return (
        <div data-testid="reg-div" style={registerStyles.container}>
            <form data-testid="reg-form" style={registerStyles.form} onSubmit={submitRegistration}>
                <h2 data-testid="form-head" style={registerStyles.title}>Create an Account</h2>
                <input type="text" name="firstName" placeholder="First Name" required style={registerStyles.input} />
                <input type="text" name="lastName" placeholder="Last Name" required style={registerStyles.input} />
                <input type="email" name="email" placeholder="Email" required style={registerStyles.input} />
                <input type="password" name="password" placeholder="Password" required style={registerStyles.input} />
                <input type="password" name="confirm_password" placeholder="Confirm Password" required style={registerStyles.input} />
                <button type="submit" style={registerStyles.button}>Register</button>
            </form>
        </div>
    );
};

export default Register;
