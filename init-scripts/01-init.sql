-- Initialize the database for the CEP service
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Create the schema if it doesn't exist
CREATE SCHEMA IF NOT EXISTS cep_service;

-- Set the search path
SET search_path TO cep_service, public;

-- Create the cep_logs table if it doesn't exist
CREATE TABLE IF NOT EXISTS cep_logs (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    cep VARCHAR(8) NOT NULL,
    request_date TIMESTAMP NOT NULL,
    response TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes
CREATE INDEX IF NOT EXISTS idx_cep_logs_cep ON cep_logs(cep);
CREATE INDEX IF NOT EXISTS idx_cep_logs_request_date ON cep_logs(request_date);

-- Grant privileges
GRANT ALL PRIVILEGES ON SCHEMA cep_service TO postgres;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA cep_service TO postgres;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA cep_service TO postgres;

-- Add comments
COMMENT ON TABLE cep_logs IS 'Stores logs of CEP lookup requests';
COMMENT ON COLUMN cep_logs.id IS 'Unique identifier for the log entry';
COMMENT ON COLUMN cep_logs.cep IS 'The CEP (postal code) that was looked up';
COMMENT ON COLUMN cep_logs.request_date IS 'The date and time when the request was made';
COMMENT ON COLUMN cep_logs.response IS 'The response from the CEP lookup service';
COMMENT ON COLUMN cep_logs.created_at IS 'The date and time when the log entry was created';