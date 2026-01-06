CREATE TABLE patients (
    patient_id UUID PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    dob DATE NOT NULL,
    gender VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_patient_lookup ON patients (lower(first_name), lower(last_name), dob);

CREATE TABLE source_systems (
    source_id SERIAL PRIMARY KEY,
    system_name VARCHAR(100) UNIQUE NOT NULL, -- 'HOSPITAL_A', 'CLINIC_B'
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE identifiers (
    identifier_id SERIAL PRIMARY KEY,
    patient_id UUID NOT NULL REFERENCES patients(patient_id) ON DELETE CASCADE,
    identifier_type VARCHAR(50) NOT NULL, -- 'NATIONAL_ID', 'PHONE', 'EMAIL', 'PASSPORT'
    identifier_value VARCHAR(100) NOT NULL,
    is_primary BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(identifier_type, identifier_value)
    
);


CREATE TABLE patient_source_records (
    source_record_id SERIAL PRIMARY KEY,
    patient_id UUID NOT NULL REFERENCES patients(patient_id) ON DELETE CASCADE,
    source_id INTEGER NOT NULL REFERENCES source_systems(source_id),
    local_mrn VARCHAR(50) NOT NULL, 
    raw_data JSONB, 
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(source_id, local_mrn) 
);

CREATE TABLE patient_links (
    link_id SERIAL PRIMARY KEY,
    patient_id_1 UUID NOT NULL REFERENCES patients(patient_id),
    patient_id_2 UUID NOT NULL REFERENCES patients(patient_id),
    link_status VARCHAR(20) DEFAULT 'REVIEW', -- 'AUTO', 'REVIEW', 'RESOLVED'
    match_score DECIMAL(5,2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_different_patients CHECK (patient_id_1 <> patient_id_2)
);