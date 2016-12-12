package com.beeva.ryd.vision.poc.cloudvision;

import com.google.api.client.auth.oauth2.Credential;

interface CredentialsFactory {

    Credential create();

}
